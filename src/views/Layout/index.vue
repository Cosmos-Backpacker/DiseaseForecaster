<script setup>
import LayoutNav from "@/views/Layout/components/LayoutNav.vue";
import LayoutHeader from "@/views/Layout/components/LayoutHeader.vue";
import LayoutFooter from "@/views/Layout/components/LayoutFooter.vue";
import LayoutFixed from "@/views/Layout/components/LayoutFixed.vue";

</script>


<!--下面排放的顺序不能随便改变-->
<!--RouterView是动态展示的而其他的是固定死的-->
<template>

  <div class="container">


    <layout-fixed></layout-fixed>
    <LayoutNav></LayoutNav>
    <LayoutHeader></LayoutHeader>

    <!--  子组件将显示在这里-->
    <div class="subRouters">
      <RouterView/>

    </div>

    <LayoutFooter></LayoutFooter>

  </div>
</template>

<style scoped>
.container{
  width: 100%;
  grid-template-rows: auto;
}


</style>
