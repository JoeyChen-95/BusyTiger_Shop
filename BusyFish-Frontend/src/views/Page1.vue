<template>
  <div class="page-1">
    名字：<input type="text" v-model="value">
  </div>
</template>

<script type="text/javascript">
export default {
  name: "Page1",
  mounted() {
    this.value=this.$route.params.name;
  },
  data(){
    return {
      value:''
    }
  }
}
</script>

<style scoped>

</style>
