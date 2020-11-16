<template>
  <div>
    <v-btn text color="white" @click="toggle" style="background-color:rgb(22, 150, 245); height: 80%; width: 50%; font-size: 1.7vw;">{{buttonText}}</v-btn>
     <vue-speech v-if="isClick" @onTranscriptionEnd="onEnd" lang="ko" />
  </div>
</template>

<script>
import Vue from 'vue'
import VueSpeech from 'vue-speech'
import axios from 'axios'
// import $ from 'jquery';
Vue.use(VueSpeech)
export default {
  props: {
    lang: {
      type: String,
      default: 'ko'
    }
  },
    data() {
    return {
      isClick:false,
      buttonText:"버튼을 누르고 말을 해보세요",
      commands : [],
      transcription: [],
      runtimeTranscription: [],
    }
  },
  mounted(){
            this.checkApi()
          },
    methods:{
      checkApi () {
      window.SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition
      if (!window.SpeechRecognition && process.env.NODE_ENV !== 'production') {
        throw new Error('Speech Recognition does not exist on this browser. Use Chrome or Firefox')
      }
      if (!window.SpeechRecognition) {
        return
      }
      const recognition = new window.SpeechRecognition()
      recognition.lang = this.lang
      recognition.interimResults = true
      recognition.addEventListener('result', event => {
        const text = Array.from(event.results)
          .map(result => result[0])
          .map(result => result.transcript)
          .join('')
        this.runtimeTranscription = text
      })
      recognition.addEventListener('end', () => {
        if (this.runtimeTranscription !== '') {
          this.transcription.push(this.runtimeTranscription)
          this.$emit('onTranscriptionEnd', {
            transcription: this.transcription,
            lastSentence: this.runtimeTranscription
          })
        }
        this.runtimeTranscription = ''
        // recognition.start()
      })
      recognition.start()
    },
        onEnd({lastSentence}){
            this.commands.push(lastSentence)
        },
        toggle(){
          this.isClick = !this.isClick;
          if(this.isClick){
            this.buttonText="말을 마쳤다면 버튼을 눌러주세요."
          }else{
            this.buttonText="버튼을 누르고 말을 해보세요"
            axios.post('https://k3b102.p.ssafy.io:9999/cobit/speech/analyze1', this.commands
          )
          .then(function (response) {
            // console.log(response);
            // window.location.reload();
          })
          .catch(function (error) {
            // console.log(error);
          });
          this.commands = [];
            this.transcription = [];
            this.runtimeTranscription = [];
          }
          
        },
         
          
    }
}
</script>