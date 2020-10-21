!<template>
  <div class="webgl-content">
      <div id="ball" style="width: 960px; height: 600px"></div>
      <div class="footer">
        <div class="webgl-logo"></div>
        <div class="fullscreen" onclick="unityBall.SetFullscreen(1)"></div>
        <div class="title">Ball</div>
      </div>
    </div>
</template>

<script>
export default {
  mounted(){
    let jsScript = document.createElement('script');    
    jsScript.setAttribute('src',"js파일경로");
    document.head.appendChild(jsScript);
  }
}
</script>

<style>

</style>