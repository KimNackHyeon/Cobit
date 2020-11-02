<template>
  <transition name="modal">
    <div class="diff-modal-mask">
      <i class="fas fa-times" @click.self="$emit('close')"></i>
      <div class="diff-modal-wrap">
        <div class="diff-modal-content diff-modal-on-stage">
          <img src="@/assets/images/stage1.png" alt="초급">
          <div class="diff-modal-name">초 급</div>
        </div>
        <div v-if="false" class="diff-modal-content diff-modal-on-stage">
          <img src="@/assets/images/diff2.jpg" alt="중급">
          <div class="diff-modal-name">중 급</div>
        </div>
        <div class="diff-modal-content">
          <div class="diff-modal-unlock"><i class="fas fa-lock"></i></div>
          <img src="@/assets/images/diff2.jpg" alt="중급">
          <div class="diff-modal-name">중 급</div>
        </div>
        <div v-if="false" class="diff-modal-content diff-modal-on-stage">
          <img src="@/assets/images/diff3.jpg" alt="고급">
          <div class="diff-modal-name">고 급</div>
        </div>
        <div class="diff-modal-content">
          <div class="diff-modal-unlock"><i class="fas fa-lock"></i></div>
          <img src="@/assets/images/diff3.jpg" alt="고급">
          <div class="diff-modal-name">고 급</div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'DifficultyModal',
  data() {
    return {
    }
  },
  watch: {
  },
  computed: {
  },
  created() {
  },
  mounted() {
  },
  methods: {
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

.diff-modal-mask {
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

.diff-modal-wrap {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  transition: 0.3s ease;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.diff-modal-mask .fa-times {
  position: absolute;
  top: 4%;
  right: 4%;
  cursor: pointer;
  color: rgba(0,0,0,0.7);
  font-size: 40px;
  transition: all .3s;
}

.diff-modal-mask .fa-times:hover {
  color: #fff;
}

.diff-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 18%;
  height: 50%;
  background-color: #fff;
  border-radius: 30px;
  position: relative;
  overflow: hidden;
}

.diff-modal-on-stage {
  box-shadow: 1px 1px 8px -1px #ffffff;
  cursor: pointer;
}

.diff-modal-on-stage::after {
  content: "";
  position: absolute;
  top: -50%;
  left: -60%;
  width: 80%;
  height: 200%;
  opacity: 0;
  transform: rotate(30deg);
  background: rgba(255, 255, 255, 0.13);
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 0.13) 0%,
    rgba(255, 255, 255, 0.3) 15%,
    rgba(255, 255, 255, 0.6) 32%,
    rgba(255, 255, 255, 0.0) 100%
  );
}

.diff-modal-on-stage:hover::after {
  opacity: 1;
  left: 140%;
  transition-property: left, top, opacity;
  transition-duration: .8s, .8s, 0.15s;
  transition-timing-function: ease;
}

.diff-modal-on-stage:active::after {
  opacity: 0;
}

.diff-modal-content .diff-modal-unlock {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
  color: #fff;
  padding-bottom: 30px;
}

.diff-modal-content > img {
  width: 100%;
  height: 80%;
  border-top-left-radius: 30px;
  border-top-right-radius: 30px;
  /* background-color: grey; */
}

.diff-modal-content .diff-modal-name {
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'BMJUA';
  font-size: 30px;
  background-color: #a4d4ff;
  border-bottom-left-radius: 30px;
  border-bottom-right-radius: 30px;
}
</style>