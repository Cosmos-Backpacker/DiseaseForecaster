<script setup>


//模拟图片数据
const bannerList = ref([
  {
    id:1,
    url:"https://pic.imgdb.cn/item/65e706db9f345e8d037580c2.webp"
  },
  {
    id:2,
    url: "https://pic.imgdb.cn/item/65e7065e9f345e8d0373e057.jpg"
  },
  {
    id:3,
    url: "https://pic.imgdb.cn/item/65e70a9b9f345e8d03824bf5.jpg"
  },
  {
    id:4,
    url: "https://pic.imgdb.cn/item/65e70a089f345e8d038064a9.jpg"
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
  width: 1240px;
  height: 500px;
  position: absolute;
  left: 0;
  top: 0;
  z-index: 98;

  img {
    width: 100%;
    height: 500px;
  }
}
</style>