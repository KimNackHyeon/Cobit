<template>
  <transition name="modal">
    <div class="diff-modal-mask">
      <i class="fas fa-times" @click.self="$emit('close')"></i>
      <div class="diff-modal-wrap">
        <div class="diff-modal-content">
        </div>
        <div class="diff-modal-content">
        </div>
        <div class="diff-modal-content">
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
</style>