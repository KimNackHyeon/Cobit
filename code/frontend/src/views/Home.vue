<template>
  <div class="home">
    <!-- 제목 -->
    <div class="wrapper">
      <div class="titlebox">
        <h1 data-heading="Cobit">Cobit</h1>
        <h2>놀면서 코딩 습관 + 논리력 기르기</h2>        
      </div>
      <div class="btnsbox">
        <button class="learn-more">로그인하기</button>
        <router-link to="/mypage">
          <button class="learn-more" style="float: right">체험하기</button>
        </router-link>
      </div>
    </div>
    
    
  </div>
</template>

<script>
import "../css/home.scss"

export default {
  name: 'Home',
}
</script>

<style scoped>

</style>