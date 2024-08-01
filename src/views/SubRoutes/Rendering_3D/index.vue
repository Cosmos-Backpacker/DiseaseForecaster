<script setup>
import {ref, onMounted} from 'vue';
import ForceGraph3D from "3d-force-graph";
import neo4j from 'neo4j-driver';

const db = {
  uri: "bolt://localhost:7687",
  user: "neo4j",
  password: "11111111",
};

const myGraph = ref(null);
const graphData = ref(null);

onMounted(() => {
  initGraph();
});


const initGraph = async () => {
  // ... (省略了大部分代码，保持原样，只做必要的转换)
  myGraph.value = ForceGraph3D({
    controlType: "trackball", // orbit沿2d轨迹绕着拖动，fly 固定不动
    rendererConfig: {antialias: true, alpha: true},
    // ... (省略了配置代码)
  })(document.getElementById('graph'))

      // ... (省略了画布配置代码)
      /*------------------------------------------- 画布配置 -------------------------------------------*/
      .backgroundColor("black") // 背景颜色，支持内置颜色和RGB
      .width(document.getElementById('graph').offsetWidth) // 画布宽度(充满父级容器)
      .height(document.getElementById('graph').offsetHeight + 150) // 画布高度(充满父级容器)
      .showNavInfo(false) // 是否显示底部导航提示信息
      /*------------------------------------------- 节点配置 -------------------------------------------*/
      .nodeRelSize(3) // 节点大小（支持数值）
      .nodeVal((node) => node.size * 0.05) // 节点大小（支持回调）
      .nodeAutoColorBy("id") // 节点颜色：根据属性划分（参数为graphData({nodes: nodes, links: links})）中nodes中每个node中的属性名称）
      .nodeAutoColorBy((node) => node.id) // 节点颜色：回调函数处理（功能同上）
      .nodeOpacity(1) // 节点透明度：回调函数处理（根据label划分）
      .nodeLabel("name") // 节点标签显示内容（鼠标滑到节点显示，支持直接写节点属性名称）
      .nodeLabel((node) => node.labels + "<br>" + JSON.stringify(node.attrs.name)) // 节点标签显示内容（鼠标滑到节点显示，也可以使用回调函数）
      .onNodeHover(
          (node) => (document.getElementById('graph').style.cursor = node ? "pointer" : null)
      ) // 鼠标滑到节点上改变指针
      .onNodeClick((node) => {
        // 点击节点事件（视角转移到该节点）
        // Aim at node from outside it
        const distance = 40;
        const distRatio = 1 + distance / Math.hypot(node.x, node.y, node.z);

        myGraph.value.cameraPosition(
            {
              x: node.x * distRatio,
              y: node.y * distRatio,
              z: node.z * distRatio,
            }, // new position
            node, // lookAt ({ x, y, z })
            3000 // ms transition duration)
        );
      })
      /*------------------------------------------- 边的配置 -------------------------------------------*/
      .linkVisibility(true) // 是否显示边
      .linkLabel((r) => r.type) // 边的标签显示（鼠标滑到边上显示）
      .linkDirectionalArrowLength(3) // 边的指向箭头长度
      .linkDirectionalArrowRelPos(1) // 边的标签显示（鼠标滑到边上显示）
      .linkCurvature(0.25) // 边的透明度
      .linkDirectionalParticles(5) // 边粒子：数量
      .linkDirectionalParticleSpeed(1) // 边粒子：移动速度
      .linkDirectionalParticleWidth(0.3) // 边粒子：大小
      .linkColor(() => "RGB(170,170,170)") // 边颜色
      .linkAutoColorBy((r) => r.type) // 边颜色自动化分
      .linkOpacity(0.5); // 边透明度（越小越透明）


  // 加载数据
  const graph_info = await getCyperResult(1000);
  const links = Object.values(graph_info.rel_info);
  const nodes = Object.entries(graph_info.node_info).map((entry) => {
    return {id: entry[0], labels: entry[1].labels, attrs: entry[1].attrs};
  });
  myGraph.value.graphData({nodes, links});

  // ... (省略了动态设置代码)
};




const getCyperResult = async (limit_items) => {
  const start = new Date();

  const driver = neo4j.driver(
      db.uri,
      neo4j.auth.basic(db.user,db.password)
      // neo4j.auth.basic(db.user, db.password)
  );
  const session = driver.session();
  const result = await session.run(
      'MATCH (n:Food )-[r]-(m:Disease) ' +
      'RETURN ' +
      'id(n) as source, labels(n) as source_labels, properties(n) as source_attrs, ' +
      'id(m) as target, labels(m) as target_labels, properties(m) as target_attrs, ' +
      'id(r) as link,     type(r) as r_type,          properties(r) as r_attrs ' +
      'LIMIT $limit_items ',
      {limit_items: neo4j.int(limit_items)}
  );

  /* 存储节点和边信息
   * node_info[节点ID] = {节点标签：list, 节点属性:dict}
   *   rel_info[边ID] = {  边类别：str,   边属性:dict}
   */

  const node_info = {};
  const rel_info = {};
  result.records.map((r) => {
    node_info[r.get('source').toString()] = {
      labels: r.get('source_labels').toString(),
      attrs: r.get('source_attrs')
    };
    node_info[r.get('target').toString()] = {
      labels: r.get('target_labels').toString(),
      attrs: r.get('target_attrs')
    }
    rel_info[r.get('link').toString()] = {
      type: r.get('r_type').toString(),
      attrs: r.get('r_attrs'),
      source: r.get('source').toString(),
      target: r.get('target').toString()
    }

  });
  console.log(
      Object.keys(node_info).length +
      " nodes loaded and " +
      Object.keys(rel_info).length +
      " links loaded in " +
      (new Date() - start) +
      " ms."
  );
  return {
    node_info,
    rel_info,
  };

};

</script>




<template>
  <div ref="graph" id="graph"></div>
</template>


<style scoped>

</style>