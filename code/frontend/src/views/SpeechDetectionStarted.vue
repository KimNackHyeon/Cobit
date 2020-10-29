<template>
  <div>
    <v-btn text color="white" @click="toggle" style="background-color:rgb(22, 150, 245); height: 80%; width: 50%; font-size: 1.7vw;">{{buttonText}}</v-btn>
     <vue-speech v-if="isClick" @onTranscriptionEnd="onEnd" lang="ko" />
  </div>
</template>

<script>
import Vue from 'vue'
import VueSpeech from 'vue-speech'
Vue.use(VueSpeech)
export default {
    data() {
    return {
      isClick:false,
      buttonText:"버튼을 누르고 말을 해보세요",
      commands : []
    }
  },
    methods:{
        onEnd({lastSentence}){
            this.commands.push(lastSentence)
            console.log(this.commands)
        },
        toggle(){
          this.isClick = !this.isClick;
          if(this.isClick){
            this.buttonText="말을 마쳤다면 버튼을 눌러주세요."
          }else{
            this.buttonText="버튼을 누르고 말을 해보세요"
          }
        }
    }
}
</script>
<style>
 /* html {
      font-size: 10px;
    }

    body {
      background: #ffc600;
      font-family: 'helvetica neue';
      font-weight: 200;
      font-size: 20px;
    }

    .words {
      max-width: 500px;
      margin: 50px auto;
      background: white;
      border-radius: 5px;
      box-shadow: 10px 10px 0 rgba(0,0,0,0.1);
      padding: 1rem 2rem 1rem 5rem;
      background: -webkit-gradient(linear, 0 0, 0 100%, from(#d9eaf3), color-stop(4%, #fff)) 0 4px;
      background-size: 100% 3rem;
      position: relative;
      line-height: 3rem;
    }
    
    p {
      margin: 0 0 3rem;
    }

    .words:before {
      content: '';
      position: absolute;
      width: 4px;
      top: 0;
      left: 30px;
      bottom: 0;
      border: 1px solid;
      border-color: transparent #efe4e4;
    } */
</style>