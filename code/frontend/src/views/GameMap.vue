<template>
  <div class="wrap">
    <div class="map-container">
      <div class="map-header">
        <div class="back-btn"><i class="fas fa-chevron-left"></i>돌아가기</div>
      </div>
      <div class="map-body">
        <div class="map-stage-box">
          <div class="stage-num">1</div>
          <div class="stage-area"></div>
          <div class="stage-star"></div>
        </div>
        <div class="map-road-box"></div>
        <div class="map-stage-box">2</div>
        <div class="map-road-box"></div>
        <div class="map-stage-box">3</div>
        <div class="map-road-box"></div>
        <div class="map-stage-box">4</div>
        <div class="map-road-box"></div>
        <div class="map-stage-box">5</div>
        <div class="map-road-box"></div>
      </div>
      <div class="map-footer">
        <div class="map-gauge">
          <i class="fas fa-star"></i>
          <div class="map-1star-gauge"></div>
          <div class="map-2star-gauge"></div>
          <div class="map-3star-gauge"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GameMap',
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
      console.log('Base')
    },
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.map-container {
  margin-top: 50px;
  width: 100%;
  height: calc(100vh - 100px);
  background-color: bisque;
}

.map-header .back-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 180px;
  height: 60px;
  background-color: #a4d4ff;
  font-size: 30px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
}

.map-header .back-btn .fa-chevron-left {
  margin: 0;
  margin-right: 10px;
}

.map-body {
  display: flex;
  width: 100%;
  height: 70%;
  margin: 20px 0;
  justify-content: center;
}

</style>