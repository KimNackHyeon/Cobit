<template>
  <transition name="modal">
    <div class="stage-modal-mask" @mousedown.self="$emit('close')">
      <div class="stage-modal-wrap">
        <div class="stage-modal-content">
          <div class='stage-modal-header'>
            <img src="@/assets/images/blue-ribbon2.png" alt="img">
            <div class="stage-modal-name">Stage {{ StageNum }}</div>
          </div>
          <div class='stage-modal-body'>
            <div class="stage-modal-star-area">
              <i v-for="(star,idx) in StageDetail.star" :key="`m-star+${idx}`" class="fas fa-star star"></i>
              <i v-for="(unstar,idx) in StageDetail.unstar" :key="`m-unstar+${idx}`" class="fas fa-star unstar"></i>
            </div>
            <div class="stage-modal-img-area">
              <img src="@/assets/images/penguin-stage-default.jpg" alt="stage-img">
            </div>
            <div class="stage-modal-mission-area">
              <div>임무</div>
              <div>{{StageDetail.content}}</div>
            </div>
          </div>
          <div class="stage-modal-footer" @click="startGame">
            <div class="stage-modal-btn">start</div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'StageModal',
  data() {
    return {
    }
  },
  watch: {
  },
  computed: {
    ...mapState(['StageDetail', 'StageNum','StageType']),
  },
  created() {
  },
  mounted() {
  },
  methods: {
    startGame(){
      this.$cookies.set('stageInfo', {stageNum : this.StageNum, stageType : this.StageType});
      this.$router.push('/codeblock')
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
  max-width: 30vw;
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

.stage-modal-header > img {
  width: 80%;
  height: 100px;
}

.stage-modal-header .stage-modal-name {
  position: absolute;
  top: 30%;
  left: 40%;
  transform: translate(-50%, -50%);
  color: #fff;
}

.stage-modal-body .stage-modal-star-area {
  font-size: 40px;
}

.stage-modal-body .stage-modal-star-area i {
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
  margin: 0 10px;
}

.stage-modal-body .stage-modal-star-area .star {
  color: rgb(243, 243, 0);
}

.stage-modal-body .stage-modal-star-area .unstar {
  color: #fff;
}

.stage-modal-body .stage-modal-img-area {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.stage-modal-img-area > img {
  width: 80%;
  height: 100%;
  border-radius: 20px;
}

.stage-modal-body .stage-modal-mission-area {
  width: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stage-modal-mission-area > div {
  font-size: 18px;
  font-family: 'BMJUA';
  margin-bottom: 10px;
}

.stage-modal-mission-area > div:nth-child(1) {
  background-color: tomato;
  width: 50px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
  margin-right: 10px;
  color: #fff;
  letter-spacing: 3px;
  padding-left: 3px;
}

.stage-modal-footer .stage-modal-btn {
  width: 80%;
  height: 50px;
  border-radius: 30px;
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

.stage-modal-footer .stage-modal-btn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}

/* @media (max-width: 1280px) {
  .stage-modal-wrap {
    max-width: 60vw;
  }
  .stage-modal-content {
    padding: 3vh 1vw;
  }
  .stage-modal-content .stage-modal-header {
    font-size: 4vw;
  }
  .stage-modal-body .stage-modal-img-area {
    margin: 3vw 0;
  }
  .stage-modal-header > img {
    width: 80%;
    height: 14vw;
  }
  .stage-modal-body .stage-modal-star-area {
    font-size: 6vw;
  }
  .stage-modal-body .stage-modal-star-area i {
    text-shadow: -.2vw 0 #000,
                  0 .2vw #000,
                  .2vw 0 #000,
                  0 -.2vw #000;
    margin: 0 1.5vw;
  }
  .stage-modal-mission-area > div {
    font-size: 2.5vw;
  }
  .stage-modal-mission-area > div:nth-child(1) {
    width: 7vw;
    height: 4vw;
  }
  .stage-modal-footer .stage-modal-btn {
    width: 80%;
    height: 7vw;
    font-size: 4vw;
  }
} */
</style>