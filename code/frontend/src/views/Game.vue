<template>
  <!-- <div class="webgl-content">
      <div id="unityContainer" style="width: 960px; height: 600px"></div>
      <div class="footer">
        <div class="webgl-logo"></div>
        <div class="fullscreen" onclick="unityInstance.SetFullscreen(1)"></div>
        <div class="title">Game</div>
      </div>
    </div> -->
    <unity src="Build/unity.json" width="1000" height="600" unityLoader="Build/UnityLoader.js"></unity>
</template>

<script>
import Unity from 'vue-unity-webgl'

export default {
  components: { Unity },
  methods: {
    onClick () {
      this.$refs.myInstance.message("object", "method", "param")
    }
  }
  
}
</script>

<style>

</style>