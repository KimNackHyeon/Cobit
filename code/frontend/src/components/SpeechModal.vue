<template>
  <transition name="modal">
    <div class="speech-modal-mask">
      <div class="speech-modal-wrap">
        <div class="speech-modal-content">
          <div class='speech-modal-header'>
            <div>
              <img src="../assets/images/lightbulb.png" alt="">
              <p>말하기 힌트</p>
            </div>
          </div>
          <div class='speech-modal-body'>
            <p>{{ hint }}</p>
            <p>말해보세요</p>
            <p v-if="extraHint">예를들어 {{ extraHint }}</p>
            <p v-if="extraHint">이렇게 말해보세요</p>
          </div>
          <div class="speech-modal-footer">
            <div class="speech-modal-btn" @click.self="$emit('close')">확인</div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'SpeechModal',
  data() {
    return {
      hint: '',
      extraHint: '',
    }
  },
  watch: {
  },
  computed: {
    ...mapState(['SpeechType', 'InStageNum']),
  },
  created() {
  },
  mounted() {
    this.setHint();
  },
  methods: {
    setHint() {
      if(this.SpeechType == 1) {
        this.hint = '왼쪽 · 오른쪽 · 위쪽 · 아래쪽을'
        this.extraHint = '오른쪽으로 네번 가'
      } else {
        this.hint = '점프를'
      }
    }
  }
}
</script>

<style scoped>
.modal-enter-active, .modal-leave-active {
  transition: opacity 0.4s;

}

.modal-leave-active {
  transition: opacity 0.6s ease 0.4s;
}

.modal-enter, .modal-leave-to {
  opacity: 0;
}

.speech-modal-mask {
  position: fixed;
  z-index: 99999;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, .5);
  transition: opacity .3s ease;
}

.speech-modal-wrap {
  max-width: 400px;
  width: 100%;
  padding: 10px;
  transition: 0.3s ease;
  background-color: #fff;
  border-radius: 30px;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .speech-modal-wrap,
.modal-leave-active .speech-modal-wrap {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.speech-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30px 10px;
  position: relative;
}

.speech-modal-content .speech-modal-header {
  font-size: 35px;
  font-family: 'BMJUA';
  width: 100%;
  height: 20%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.speech-modal-content .speech-modal-header > div {
  display: flex;
  justify-content: center;
  align-items: center;
}

.speech-modal-content .speech-modal-header::after {
  content: "";
  display: block;
  width: 60%;
  margin-left: 2%;
  border-bottom: 3.5px solid black;
}

.speech-modal-content .speech-modal-body {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.speech-modal-content .speech-modal-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.speech-modal-header > div > img {
  width: 10%;
  padding-bottom: 10px;
  margin-right: 5px;
}

.speech-modal-body > p {
  font-family: 'BMJUA';
  font-size: 20px;
  margin-bottom: 5px;
}

.speech-modal-body > p:nth-child(2) {
  margin-bottom: 20px;
}

.speech-modal-footer .speech-modal-btn {
  width: 80%;
  height: 50px;
  border-radius: 30px;
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #a4d4ff;
  font-size: 25px;
  font-family: 'BMJUA';
  cursor: pointer;
  transition: box-shadow .3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
}

.speech-modal-footer .speech-modal-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

</style>