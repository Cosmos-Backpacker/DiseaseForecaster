import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
//这个方法好像没有什么用处

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
