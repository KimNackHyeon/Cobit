<template>
  <!-- <div class="webgl-content">
      <div id="unityContainer" style="width: 960px; height: 600px"></div>
      <div class="footer">
        <div class="webgl-logo"></div>
        <div class="fullscreen" onclick="unityInstance.SetFullscreen(1)"></div>
        <div class="title">Game</div>
      </div>
    </div> -->
    <div>
      <unity src="Build/unity.json" width="1000" height="600" unityLoader="Build/UnityLoader.js" ref="myInstance" :hideFooter="true"></unity>
      <button @click="onClick">버튼</button>
    </div>
</template>

<script>
import Unity from 'vue-unity-webgl'

export default {
  components: { Unity },
  methods: {
    onClick () {
      alert("click");
      this.$refs.myInstance.message("New Unity Project (2)", "method", "param")
    }
  }
  
}
</script>

<style>

</style>