<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>
      <h1 class="item-title">Item Status</h1>
      <b-list-group class="badge-font">
        <b-list-group-item>
          <b-badge variant="success">ACTIVE</b-badge>
          <div>This item is available and everyone can see and buy this item. </div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="danger">SOLD</b-badge>
          <div>This item is sold, someone has already bought it. This item cannot be bought.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="warning">BANNED</b-badge>
          <div>This item is banned by BusyTiger administrator due violation of our community rule.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="warning">PRIVATE</b-badge>
          <div>This item is invisible to everyone except its seller.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="warning">PENDING</b-badge>
          <div>This item is not available now and is being checked by BusyTiger administrator. For more information, contact 707973013@qq.com</div>
        </b-list-group-item>

      </b-list-group>
    </div>
  </div>

</template>

<script>
export default {
  name: "ItemStatusHint"
}
</script>

<style scoped>
.item-title{
  font-size: 32px;
  font-weight: bold;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.3);
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}
.badge-font{
  font-size: 30px;
  font-weight: bold;
}
</style>
