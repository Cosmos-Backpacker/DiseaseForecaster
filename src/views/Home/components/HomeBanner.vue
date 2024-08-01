<script setup>


//模拟图片数据
const bannerList = ref([
  {
    id:1,
    url:"src/assets/home/banner/banner1.jpg"
  },
  {
    id:2,
    url: "src/assets/home/banner/banner2.jpg"
  },
  {
    id:3,
    url: "src/assets/home/banner/banner3.jpg"
  },
  {
    id:4,
    url: "src/assets/home/banner/banner4.jpg"
  }

])

</script>

<template>
  <div class="home-banner">
    <el-carousel height="500px">
      <el-carousel-item v-for="item in bannerList" :key="item.id">
        <img :src="item.url" alt="">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>


<style scoped lang='scss'>
.home-banner {
  width: 1200px;
  height: 500px;

  img {
    width: 100%;
    height: 500px;
  }
}
</style>
