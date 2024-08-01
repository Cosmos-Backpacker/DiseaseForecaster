// vite.config.js
import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "file:///D:/VueProject/VueDemo/Demo1/node_modules/vite/dist/node/index.js";
import AutoImport from "file:///D:/VueProject/VueDemo/Demo1/node_modules/unplugin-auto-import/dist/vite.js";
import Components from "file:///D:/VueProject/VueDemo/Demo1/node_modules/unplugin-vue-components/dist/vite.js";
import { ElementPlusResolver } from "file:///D:/VueProject/VueDemo/Demo1/node_modules/unplugin-vue-components/dist/resolvers.js";
import vue from "file:///D:/VueProject/VueDemo/Demo1/node_modules/@vitejs/plugin-vue/dist/index.mjs";
var __vite_injected_original_import_meta_url = "file:///D:/VueProject/VueDemo/Demo1/vite.config.js";
var vite_config_default = defineConfig({
  plugins: [
    vue(),
    // 配置插件
    AutoImport({
      imports: ["vue", "vue-router", "pinia"],
      //加入这句以后每个vue里都不需要再用import来导入
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver({ importStyle: "sass" })]
    })
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", __vite_injected_original_import_meta_url))
    }
  },
  css: {
    preprocessorOptions: {
      scss: {
        // 自动导入定制化样式文件进行样式覆盖
        additionalData: `
          @use "@/styles/element/index.scss" as *;         
          @use "@/styles/var.scss" as *;
        `
      }
    }
  },
  server: {
    host: "0.0.0.0",
    port: 5713,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "")
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxWdWVQcm9qZWN0XFxcXFZ1ZURlbW9cXFxcRGVtbzFcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFZ1ZVByb2plY3RcXFxcVnVlRGVtb1xcXFxEZW1vMVxcXFx2aXRlLmNvbmZpZy5qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVnVlUHJvamVjdC9WdWVEZW1vL0RlbW8xL3ZpdGUuY29uZmlnLmpzXCI7aW1wb3J0IHsgZmlsZVVSTFRvUGF0aCwgVVJMIH0gZnJvbSAnbm9kZTp1cmwnXG5cbmltcG9ydCB7IGRlZmluZUNvbmZpZyB9IGZyb20gJ3ZpdGUnXG5cbi8vIFx1NUYxNVx1NTE2NVx1NjNEMlx1NEVGNlxuaW1wb3J0IEF1dG9JbXBvcnQgZnJvbSAndW5wbHVnaW4tYXV0by1pbXBvcnQvdml0ZSdcbmltcG9ydCBDb21wb25lbnRzIGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3ZpdGUnXG5pbXBvcnQgeyBFbGVtZW50UGx1c1Jlc29sdmVyIH0gZnJvbSAndW5wbHVnaW4tdnVlLWNvbXBvbmVudHMvcmVzb2x2ZXJzJ1xuXG5cbmltcG9ydCB2dWUgZnJvbSAnQHZpdGVqcy9wbHVnaW4tdnVlJ1xuXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWcoe1xuICBwbHVnaW5zOiBbXG4gICAgdnVlKCksXG4gICAgLy8gXHU5MTREXHU3RjZFXHU2M0QyXHU0RUY2XG4gICAgQXV0b0ltcG9ydCh7XG4gICAgICBpbXBvcnRzOltcInZ1ZVwiLFwidnVlLXJvdXRlclwiLFwicGluaWFcIl0sICAgLy9cdTUyQTBcdTUxNjVcdThGRDlcdTUzRTVcdTRFRTVcdTU0MEVcdTZCQ0ZcdTRFMkF2dWVcdTkxQ0NcdTkwRkRcdTRFMERcdTk3MDBcdTg5ODFcdTUxOERcdTc1MjhpbXBvcnRcdTY3NjVcdTVCRkNcdTUxNjVcbiAgICAgIHJlc29sdmVyczogW0VsZW1lbnRQbHVzUmVzb2x2ZXIoKV0sXG4gICAgfSksXG4gICAgQ29tcG9uZW50cyh7XG4gICAgICByZXNvbHZlcnM6IFtFbGVtZW50UGx1c1Jlc29sdmVyKHtpbXBvcnRTdHlsZTonc2Fzcyd9KV0sXG4gICAgfSksXG4gIF0sXG4gIHJlc29sdmU6IHtcbiAgICBhbGlhczoge1xuICAgICAgJ0AnOiBmaWxlVVJMVG9QYXRoKG5ldyBVUkwoJy4vc3JjJywgaW1wb3J0Lm1ldGEudXJsKSlcbiAgICB9XG4gIH0sXG4gIGNzczoge1xuICAgIHByZXByb2Nlc3Nvck9wdGlvbnM6IHtcbiAgICAgIHNjc3M6IHtcbiAgICAgICAgLy8gXHU4MUVBXHU1MkE4XHU1QkZDXHU1MTY1XHU1QjlBXHU1MjM2XHU1MzE2XHU2ODM3XHU1RjBGXHU2NTg3XHU0RUY2XHU4RkRCXHU4ODRDXHU2ODM3XHU1RjBGXHU4OTg2XHU3NkQ2XG4gICAgICAgIGFkZGl0aW9uYWxEYXRhOiBgXG4gICAgICAgICAgQHVzZSBcIkAvc3R5bGVzL2VsZW1lbnQvaW5kZXguc2Nzc1wiIGFzICo7ICAgICAgICAgXG4gICAgICAgICAgQHVzZSBcIkAvc3R5bGVzL3Zhci5zY3NzXCIgYXMgKjtcbiAgICAgICAgYCxcbiAgICAgIH1cbiAgICB9XG4gIH0sXG5cbiAgc2VydmVyOntcbiAgICBob3N0OicwLjAuMC4wJyxcbiAgICBwb3J0OjU3MTMsXG4gICAgcHJveHk6e1xuICAgICAgJy9hcGknOntcbiAgICAgICAgdGFyZ2V0OidodHRwOi8vbG9jYWxob3N0OjgwODAnLFxuICAgICAgICB3czogdHJ1ZSxcbiAgICAgICAgY2hhbmdlT3JpZ2luOnRydWUsXG4gICAgICAgIHJld3JpdGU6cGF0aCA9PiBwYXRoLnJlcGxhY2UoL15cXC9hcGkvLCcnKVxuICAgICAgfVxuICAgIH1cbiAgfVxuXG59KVxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUEyUSxTQUFTLGVBQWUsV0FBVztBQUU5UyxTQUFTLG9CQUFvQjtBQUc3QixPQUFPLGdCQUFnQjtBQUN2QixPQUFPLGdCQUFnQjtBQUN2QixTQUFTLDJCQUEyQjtBQUdwQyxPQUFPLFNBQVM7QUFWcUosSUFBTSwyQ0FBMkM7QUFZdE4sSUFBTyxzQkFBUSxhQUFhO0FBQUEsRUFDMUIsU0FBUztBQUFBLElBQ1AsSUFBSTtBQUFBO0FBQUEsSUFFSixXQUFXO0FBQUEsTUFDVCxTQUFRLENBQUMsT0FBTSxjQUFhLE9BQU87QUFBQTtBQUFBLE1BQ25DLFdBQVcsQ0FBQyxvQkFBb0IsQ0FBQztBQUFBLElBQ25DLENBQUM7QUFBQSxJQUNELFdBQVc7QUFBQSxNQUNULFdBQVcsQ0FBQyxvQkFBb0IsRUFBQyxhQUFZLE9BQU0sQ0FBQyxDQUFDO0FBQUEsSUFDdkQsQ0FBQztBQUFBLEVBQ0g7QUFBQSxFQUNBLFNBQVM7QUFBQSxJQUNQLE9BQU87QUFBQSxNQUNMLEtBQUssY0FBYyxJQUFJLElBQUksU0FBUyx3Q0FBZSxDQUFDO0FBQUEsSUFDdEQ7QUFBQSxFQUNGO0FBQUEsRUFDQSxLQUFLO0FBQUEsSUFDSCxxQkFBcUI7QUFBQSxNQUNuQixNQUFNO0FBQUE7QUFBQSxRQUVKLGdCQUFnQjtBQUFBO0FBQUE7QUFBQTtBQUFBLE1BSWxCO0FBQUEsSUFDRjtBQUFBLEVBQ0Y7QUFBQSxFQUVBLFFBQU87QUFBQSxJQUNMLE1BQUs7QUFBQSxJQUNMLE1BQUs7QUFBQSxJQUNMLE9BQU07QUFBQSxNQUNKLFFBQU87QUFBQSxRQUNMLFFBQU87QUFBQSxRQUNQLElBQUk7QUFBQSxRQUNKLGNBQWE7QUFBQSxRQUNiLFNBQVEsVUFBUSxLQUFLLFFBQVEsVUFBUyxFQUFFO0FBQUEsTUFDMUM7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUVGLENBQUM7IiwKICAibmFtZXMiOiBbXQp9Cg==
