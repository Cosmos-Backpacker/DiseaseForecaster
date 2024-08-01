import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'

// 引入插件
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'


import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [
    vue(),
    // 配置插件
    AutoImport({
      imports:["vue","vue-router","pinia"],   //加入这句以后每个vue里都不需要再用import来导入
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver({importStyle:'sass'})],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },

  css: {
    preprocessorOptions: {
      scss: {
        // 自动导入定制化样式文件进行样式覆盖
        additionalData: `
          @use "@/styles/element/index.scss" as *;         
          @use "@/styles/var.scss" as *;
        `,
      }
    }
  },

  server:{
    host:'0.0.0.0',
    port:5713,
    proxy:{
      '/api':{
        target:'http://localhost:8080',
        ws: true,
        changeOrigin:true,
        rewrite:path => path.replace(/^\/api/,'')
      }
    }
  }

})
