<template>
  <transition name="modal">
    <div class="fail-modal-mask">
      <div class="fail-modal-wrap">
        <div class="fail-modal-content">
          <div class='fail-modal-header'>
          </div>
          <div class='fail-modal-body'>
            <div class="fail-modal-name"><i class="far fa-sad-tear"></i><span>아쉬워요</span></div>
          </div>
          <div class="fail-modal-footer">
            <div class="fail-modal-btn" @click="reStart">다시하기</div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'FailModal',
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

.fail-modal-mask {
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

.fail-modal-wrap {
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

.modal-enter .fail-modal-wrap,
.modal-leave-active .fail-modal-wrap {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.fail-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30px 10px;
  position: relative;
}

.fail-modal-content .fail-modal-header {
  font-size: 30px;
  font-family: 'BMJUA';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
}

.fail-modal-content .fail-modal-body {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.fail-modal-content .fail-modal-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.fail-modal-body .fail-modal-name {
  font-size: 40px;
  font-weight: 700;
  font-family: 'BMJUA';
  margin-bottom: 10px;
}

.fail-modal-body .fail-modal-name > i {
  font-size: 35px;
  margin-right: 10px;
}

.fail-modal-footer .fail-modal-btn {
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

.fail-modal-footer .fail-modal-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

</style>