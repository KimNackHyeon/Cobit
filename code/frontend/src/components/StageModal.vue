<template>
  <transition name="modal">
    <div class="stage-modal-mask" @mousedown.self="$emit('close')">
      <div class="stage-modal-wrap">
        <div class="stage-modal-content">
          <div class='stage-modal-header'>

          </div>
          <div class='stage-modal-body'>

          </div>
          <div class="stage-modal-footer">
            
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>


export default {
  name: 'StageModal',
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

.stage-modal-mask {
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

.stage-modal-wrap {
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

.modal-enter .stage-modal-wrap,
.modal-leave-active .stage-modal-wrap {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.stage-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30px 10px;
  position: relative;
}


.stage-modal-content .stage-modal-header {
  font-size: 30px;
  font-family: 'BMJUA';
  position: absolute;
  top: 0;
  left: 60%;
  transform: translate(-50%, -50%);
  width: 100%;
}

.stage-modal-content .stage-modal-body {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.stage-modal-content .stage-modal-footer {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>