<template>
  <transition name="modal">
    <div class="summery-modal-mask">
      <div class="summery-modal-wrap">
        <img src="../assets/images/cong2.png" alt="">
        <img src="../assets/images/cong3.png" alt="">
        <i class="fas fa-times" @click.self="$emit('close')"></i>
        <div class="summery-modal-content">
          <div class='summery-modal-header'>
            <p>축하합니다! {{ levelName }}을 완료하셨습니다</p>
          </div>
          <div class='summery-modal-body'>
            <div class="summery-modal-story">
              <p class="summery-modal-story-head"><i class="fas fa-desktop"></i> 다시보는 컴퓨터 상식</p>
              <p v-for="story in storyList" :key="story"><i class="fas fa-angle-right"></i> {{ story }}</p>
            </div>
          </div>
          <div class='summery-modal-body'>
            <div class="summery-modal-text">
              <p class="summery-modal-text-head"><i class="fas fa-code"></i> 무엇을 배웠나요?</p>
              <p v-for="summery in summeryList" :key="summery"><i class="fas fa-angle-right"></i> {{ summery }}</p>
            </div>
          </div>
          <div class="summery-modal-footer">
            <div class="summery-modal-btn" @click="goNext">{{ nextLevelName }}으로</div>
            <div class="summery-modal-btn" @click="goMain">메인으로</div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'SummeryModal',
  data() {
    return {
      levelName: '중급',
      nextLevelName: '고급',
      storyList: [],
      summeryList: [],
    }
  },
  watch: {
  },
  computed: {
    ...mapState(['InStageNum']),
  },
  created() {
  },
  mounted() {
    this.setLevel()
  },
  methods: {
    setLevel() {
      if (this.InStageNum == 5) {
        this.levelName = '초급'
        this.nextLevelName = '중급'
        this.storyList = ['컴퓨터의 두뇌는 CPU', '컴퓨터의 공부 책상은 메모리', 
                      '크리퍼는 최초의 컴퓨터 바이러스', '웜바이러스는 스스로 복제하는 나쁜 바이러스', 
                      '트로이 목마는 다른 프로그램으로 위장하는 바이러스']
        this.summeryList = ['순차 구조를 이해하고 활용할 수 있다.',
                        '프로그래밍의 원리인 순차, 함수를 활용하여 효율적인 프로그램을 만들 수 있다.',
                        '바이러스에 대해 알아보았다.']
      } else {
        this.levelName = '중급'
        this.nextLevelName = '고급'
      }
    },
    goNext() {
      if (this.levelName == '초급') {
        this.$cookies.set('stageType', 2);
        this.$router.push('/gamemap');
      } else {
        this.$cookies.set('stageType', 3);
        this.$router.push('/gamemap');
      }
    },
    goMain() {
      this.$router.push('/mypage')
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

.summery-modal-mask {
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

.summery-modal-wrap {
  max-width: 900px;
  width: 100%;
  padding: 10px;
  transition: 0.3s ease;
  background-color: #fff;
  border-radius: 30px;
  position: relative;
}

.summery-modal-wrap > img:nth-child(1) {
  width: 23%;
  position: absolute;
  top: 4%;
  left: -1%;
  transform: rotate(-40deg);
}

.summery-modal-wrap > img:nth-child(2) {
  width: 25%;
  position: absolute;
  top: 6%;
  right: -1%;
  transform: rotate(40deg);
}

.summery-modal-wrap .fa-times {
  position: absolute;
  top: 3%;
  right: 3%;
  cursor: pointer;
  color: rgba(0,0,0,0.7);
  font-size: 30px;
  transition: all .3s;
  cursor: pointer;
  z-index: 2;
}

.summery-modal-wrap .fa-times:hover {
  color: rgba(0,0,0,0.5);
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .summery-modal-wrap,
.modal-leave-active .summery-modal-wrap {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.summery-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30px 10px;
  position: relative;
}

.summery-modal-content .summery-modal-header {
  font-size: 45px;
  font-family: 'BMJUA';
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-bottom: 10px;
  position: relative;
    text-shadow: -1px 0 rgb(255, 134, 134),
                0 1px rgb(255, 134, 134),
                1px 0 rgb(255, 134, 134),
                0 -1px rgb(255, 134, 134);
}

.summery-modal-content .summery-modal-body {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px 0 10px 0;
}

.summery-modal-content .summery-modal-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.summery-modal-body .summery-modal-img {
  width: 30%;
}

.summery-modal-body .summery-modal-story {
  width: 80%;
}

.summery-modal-story > p:nth-child(1),
.summery-modal-text > p:nth-child(1) {
  font-size: 30px;
  font-weight: 500;
  margin-bottom: 15px;
  margin-left: 0;
  color: #0088ff;
}

.summery-modal-story > p,
.summery-modal-text > p {
  font-family: 'BMJUA';
  font-size: 20px;
  margin-bottom: 10px;
  margin-left: 15px;
}

.summery-modal-story > p > .fa-angle-right,
.summery-modal-text > p > .fa-angle-right {
  font-size: 15px;
}

.summery-modal-story-head > i,
.summery-modal-text-head > i {
  font-size: 25px;
}

.summery-modal-body .summery-modal-text {
  width: 80%;
}

.summery-modal-footer .summery-modal-btn {
  width: 40%;
  height: 50px;
  border-radius: 30px;
  margin: 0 5%;
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

.summery-modal-footer .summery-modal-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

@keyframes snowing {

0% {background-position: 0px 0px, 0px 0px, 0px 0px;}

100% {background-position: 500px 1000px, 400px 400px, 300px 300px;}

}
</style>