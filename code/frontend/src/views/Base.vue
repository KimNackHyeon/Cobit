<template>
  <div class="wrap">
  </div>
</template>

<script>
export default {
  name: 'Base',
  data() {
    return {
      test: '',
    }
  },
  components: {
  },
  computed: {
  },
  created() {
    // window.addEventListener('scroll', this.handleScroll)
  },
  mounted() {
  },
  watch: {
  },
  methods: {
    handleScroll() {
      // console.log('Base')
    },
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style>

</style>