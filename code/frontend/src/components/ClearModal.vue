<template>
  <transition name="modal">
    <div class="clear-modal-mask">
      <div class="clear-modal-wrap">
        <div class="clear-modal-content">
          <div class='clear-modal-header'>
            <img src="@/assets/images/blue-ribbon2.png" alt="img">
            <div class="clear-modal-stage">Stage {{ InStageNum }}</div>
          </div>
          <div class='clear-modal-body'>
            <div class="clear-modal-name">CLEAR!</div>
            <div class="clear-modal-star-area">
              <i v-for="(star,idx) in InStageStar" :key="`m-star+${idx}`" class="fas fa-star star"></i>
              <i v-for="(unstar,idx) in (3-InStageStar)" :key="`m-unstar+${idx}`" class="fas fa-star unstar"></i>
            </div>
          </div>
          <div class="clear-modal-footer">
            <div v-if="!IsLastStage" class="clear-modal-btn" @click="nextLevel">다음 단계로</div>
            <div v-if="IsLastStage" class="clear-modal-btn" @click="onModal">완료하기</div>
            <div class="clear-modal-btn" @click="reStart">다시하기</div>
          </div>
        </div>
        <div v-show="isOpen" class="clear-modal-content">
          <div class="clear-modal-toggle-btn-area">
            <div class="clear-modal-toggle-btn kor-toggle" @click="onKor">한글</div>
            <div class="clear-modal-toggle-btn eng-toggle" @click="onEng">영어</div>
          </div>
          <div v-show="isKor" class="clear-modal-code-area">
            <p>시작() {</p>
            <p class="clear-modal-inner-code" v-for='kor in CodeKor' :key="kor">{{ kor }}</p>
            <p>}</p>
          </div>
          <div v-show="!isKor" class="clear-modal-code-area">
            <p>main() {</p>
            <p class="clear-modal-inner-code" v-for='eng in Code' :key="eng">{{ eng }}</p>
            <p>}</p>
          </div>
        </div>
        <div class="clear-modal-open-area">
          <div v-if="!isOpen" class="clear-modal-open-btn" @click="openCode"><i class="fas fa-chevron-right"></i></div>
          <div v-if="isOpen" class="clear-modal-open-btn" @click="openCode"><i class="fas fa-chevron-left"></i></div>
          <div v-if="!isOpen" class="clear-modal-open-text">코드보기</div>
          <div v-if="isOpen" class="clear-modal-open-text">접기</div>
        </div>
      </div>

      <SummeryModal v-if="showModal" @close="showModal= false"/>
    </div>
  </transition>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import SummeryModal from '../components/SummeryModal.vue';

export default {
  name: 'ClearModal',
  data() {
    return {
      showModal: false,
      isOpen: false,
      isKor: true,
    }
  },
  components: {
    SummeryModal,
  },
  watch: {
  },
  computed: {
    ...mapState(['InStageNum', 'InStageStar', 'IsLastStage', 'Code', 'CodeKor']),
  },
  created() {
  },
  mounted() {
  },
  methods: {
    ...mapMutations(['setInStageNum']),
    nextLevel() {
      this.$emit('next');
      this.$emit('close')
    },
    reStart() {
      this.$emit('close')
      this.$emit('restart')
    },
    onModal() {
      this.showModal = true
    },
    openCode() {
      this.isOpen = !this.isOpen
      const BASE = document.querySelector('.clear-modal-wrap')
      const BTN = document.querySelector('.clear-modal-open-area')
      if(this.isOpen) {
        BASE.style.maxWidth = '800px';
        BTN.style.right = '45%';
        this.onKor()
      } else {
        BASE.style.maxWidth = '400px';
        BTN.style.right = '-8%';
      }
      console.log(BASE.style)
    },
    onKor() {
      this.isKor = true
      const KOR = document.querySelector('.kor-toggle')
      const ENG = document.querySelector('.eng-toggle')
      KOR.style.boxShadow = '0 0 0 0 rgba(0,0,0,0), 0 0 0 0 rgba(0,0,0,0), inset 4px 4px 6px -1px rgba(0,0,0,0.2), inset -3px -3px 4px -1px #ffffff'
      KOR.style.color = '#000'
      ENG.style.boxShadow = '6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff'
      ENG.style.color = 'rgba(0,0,0,0.5)'
    },
    onEng() {
      this.isKor = false
      const KOR = document.querySelector('.kor-toggle')
      const ENG = document.querySelector('.eng-toggle')
      ENG.style.boxShadow = '0 0 0 0 rgba(0,0,0,0), 0 0 0 0 rgba(0,0,0,0), inset 4px 4px 6px -1px rgba(0,0,0,0.2), inset -3px -3px 4px -1px #ffffff'
      ENG.style.color = '#000'
      KOR.style.boxShadow = '6px 6px 10px -1px rgba(0,0,0,0.2), -6px -6px 10px -1px #ffffff'
      KOR.style.color = 'rgba(0,0,0,0.5)'
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

.clear-modal-mask {
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

.clear-modal-wrap {
  max-width: 400px;
  width: 100%;
  padding: 10px;
  transition: 0.3s ease;
  background-color: #fff;
  border-radius: 30px;
  position: relative;
  display: flex;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .clear-modal-wrap,
.modal-leave-active .clear-modal-wrap {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.clear-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30px 10px;
  position: relative;
  width: 100%;
}

.clear-modal-content .clear-modal-header {
  font-size: 30px;
  font-family: 'BMJUA';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
}

.clear-modal-content .clear-modal-body {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.clear-modal-content .clear-modal-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.clear-modal-header > img {
  width: 100%;
  height: 100px;
}

.clear-modal-header .clear-modal-stage {
  position: absolute;
  top: 25%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
}

.clear-modal-body .clear-modal-name {
  font-size: 50px;
  font-weight: 700;
  font-family: 'BMJUA';
  margin-bottom: 10px;
}

.clear-modal-body .clear-modal-star-area {
  font-size: 40px;
  background-color: rgb(246, 246, 252);
  width: 80%;
  padding: 15px 0;
  border-radius: 20px;
  margin-top: 10px;
  display: flex;
  justify-content: center;
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

.clear-modal-body .clear-modal-star-area i {
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
  margin: 0 10px;
}

.clear-modal-body .clear-modal-star-area .star {
  color: rgb(243, 243, 0);
}

.clear-modal-body .clear-modal-star-area .unstar {
  color: #fff;
}

.clear-modal-footer .clear-modal-btn {
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

.clear-modal-footer .clear-modal-btn:nth-child(2) {
  background-color: rgba(0,0,0,0.5);
  color: rgba(255,255,255,0.7);
}

.clear-modal-footer .clear-modal-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

.clear-modal-wrap .clear-modal-open-area {
  position: absolute;
  top: 36%;
  right: -8%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: all .3s ease;
}

.clear-modal-wrap .clear-modal-open-btn {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: #d7ebfd;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all .3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
}

.clear-modal-wrap .clear-modal-open-text {
  width: 70px;
  height: 30px;
  border-radius: 20px;
  font-family: 'BMJUA';
  background-color: #d7ebfd;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all .3s ease;
}

.clear-modal-wrap .clear-modal-open-btn > i {
  font-size: 40px;
  font-weight: 600;
}

.clear-modal-wrap .clear-modal-open-btn:hover {
  background-color: #cbdff0;
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

.clear-modal-content .clear-modal-toggle-btn-area {
  display: flex;
  width: 90%;
  height: 15%;
  margin-left: 15%;
  justify-content: space-evenly;
}

.clear-modal-toggle-btn-area .clear-modal-toggle-btn {
  width: 40%;
  background-color: #a4d4ff;
  height: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
  font-family: 'BMJUA';
  cursor: pointer;
  color: rgba(0,0,0,0.5);
  transition: 0.3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
  
}

.clear-modal-toggle-btn-area .clear-modal-toggle-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

.clear-modal-content .clear-modal-code-area {
  width: 90%;
  height: 85%;
  max-height: 250px;
  margin-left: 15%;
  overflow: auto;
  border-radius: 20px;
  padding: 20px;
  background-color:rgb(249, 252, 255);
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px rgb(241, 248, 255) !important;
}

.clear-modal-code-area > p {
  font-family: 'GmarketSansMedium';
  margin-bottom: 5px;
  transition: all .3s ease;
}

.clear-modal-code-area .clear-modal-inner-code {
  margin-left: 10px;
}

.clear-modal-content .clear-modal-code-area::-webkit-scrollbar { width: 10px;}
::-webkit-scrollbar-track { background-color: #ffffff; }
::-webkit-scrollbar-thumb { background: #cee8ff; }
::-webkit-scrollbar-button { display: none; }
::-webkit-scrollbar-thumb:hover { background: #a4d5ff; }
::-webkit-scrollbar-thumb:active {background: #84c5ff; }
</style>