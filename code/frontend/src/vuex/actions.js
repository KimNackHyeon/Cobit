import axios from 'axios'

export default {
    getLoginData: function (context,email) {
        return axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${email}`)
        .then(res => {
          console.log(res);
          this.$store.commit('setKakaoUserInfo', res.data);
        })
    },
}
