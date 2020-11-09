<template>
  <transition name="modal">
    <div class="summery-modal-mask">
      <div class="summery-modal-wrap">
        <div class="summery-modal-content">
          <div class='summery-modal-header'>
            <img src="@/assets/images/blue-ribbon2.png" alt="img">
            <div class="summery-modal-stage">Stage {{ InStageNum }}</div>
          </div>
          <div class='summery-modal-body'>
            <div class="summery-modal-name">summery!</div>
            <div class="summery-modal-star-area">
              <i v-for="(star,idx) in InStageStar" :key="`m-star+${idx}`" class="fas fa-star star"></i>
              <i v-for="(unstar,idx) in (3-InStageStar)" :key="`m-unstar+${idx}`" class="fas fa-star unstar"></i>
            </div>
          </div>
          <div class="summery-modal-footer">
            <div v-if="!IsLastStage" class="summery-modal-btn" @click="nextLevel">다음 단계로</div>
            <div v-if="IsLastStage" class="summery-modal-btn" @click="onModal">완료하기</div>
            <div class="summery-modal-btn" @click="reStart">다시하기</div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { mapState, mapMutations } from 'vuex';

export default {
  name: 'summeryModal',
  data() {
    return {
    }
  },
  watch: {
  },
  computed: {
    ...mapState(['InStageNum', 'InStageStar', 'IsLastStage']),
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
  font-size: 30px;
  font-family: 'BMJUA';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
}

.summery-modal-content .summery-modal-body {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.summery-modal-content .summery-modal-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.summery-modal-header > img {
  width: 100%;
  height: 100px;
}

.summery-modal-header .summery-modal-stage {
  position: absolute;
  top: 25%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
}

.summery-modal-body .summery-modal-name {
  font-size: 50px;
  font-weight: 700;
  font-family: 'BMJUA';
  margin-bottom: 10px;
}

.summery-modal-body .summery-modal-star-area {
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

.summery-modal-body .summery-modal-star-area i {
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
  margin: 0 10px;
}

.summery-modal-body .summery-modal-star-area .star {
  color: rgb(243, 243, 0);
}

.summery-modal-body .summery-modal-star-area .unstar {
  color: #fff;
}

.summery-modal-footer .summery-modal-btn {
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

.summery-modal-footer .summery-modal-btn:nth-child(2) {
  background-color: rgba(0,0,0,0.5);
  color: rgba(255,255,255,0.7);
}

.summery-modal-footer .summery-modal-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

</style>