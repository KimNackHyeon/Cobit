<template>
  <div class="webgl-content">
      <div id="test" style="width: 960px; height: 600px"></div>
      <div class="footer">
        <div class="webgl-logo"></div>
        <div class="fullscreen" onclick="unityInstance.SetFullscreen(1)"></div>
        <div class="title">Ball</div>
      </div>
    </div>
</template>

<script>
export default {
  mounted(){
    let jsScript = document.createElement('script');    
    jsScript.setAttribute('src',"./Ball/Build/UnityLoader.js");
    document.head.appendChild(jsScript);

    jsScript = document.createElement('script');    
    jsScript.setAttribute('src',"./Ball/TemplateData/UnityProgress.js");
    document.head.appendChild(jsScript);

    jsScript = document.createElement('link');    
    jsScript.setAttribute('href',"./Ball/TemplateData/style.css");
    jsScript.setAttribute('rel',"stylesheet");
    document.head.appendChild(jsScript);

    jsScript = document.createElement('script');    
    jsScript.innerHTML = `var unityInstance = UnityLoader.instantiate("test", "./Ball/Build/unity.json", {onProgress: UnityProgress});`;
    document.head.appendChild(jsScript);

  }
}
</script>

<style>

</style>