<template>
    <div>
        <h2>TEST</h2>
        <Pano source="../assets/images/mypage_bg.png"></Pano>
        <!-- <iframe allowfullscreen src="examples/panorama_image.html"></iframe> -->
    </div>
</template>
<script>
    import { Pano } from 'vuejs-vr'
 
    export default {
        components: { Pano }
    }
</script>