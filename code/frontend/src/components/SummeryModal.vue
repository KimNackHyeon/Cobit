<template>
  <transition name="modal">
    <div class="summery-modal-mask">
      <div class="summery-modal-wrap">
        <i class="fas fa-times" @click.self="$emit('close')"></i>
        <div class="summery-modal-content">
          <div class='summery-modal-header'>
            <p>축하합니다! {{ levelName }}을 완료하셨습니다</p>
          </div>
          <div class='summery-modal-body'>
            <div class="summery-modal-img">이미지</div>
            <div class="summery-modal-story">스토리</div>
          </div>
          <div class='summery-modal-body'>
            <div class="summery-modal-text">SUMMERY</div>
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
  font-size: 40px;
  font-family: 'BMJUA';
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-bottom: 10px;
}

.summery-modal-content .summery-modal-body {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 10px 0;
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
  height: 200px;
  background-color: aqua;
}

.summery-modal-body .summery-modal-story {
  width: 60%;
  height: 200px;
  background-color: burlywood;
}

.summery-modal-body .summery-modal-text {
  width: 90%;
  height: 200px;
  background-color: cornflowerblue;
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

</style>