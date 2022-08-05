<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>
      <h1 class="item-title">Order Status</h1>
      <b-list-group class="badge-font">
        <b-list-group-item>
          <b-badge variant="info">PROCESSING</b-badge>
          <div>The order has been created and is waiting for seller's actions.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="primary">SHIPPED</b-badge>
          <div>The order has been shipped by the seller. You can track your order by the tracking No.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="success">COMPLETED</b-badge>
          <div>The order is confirmed by the buyer and completed. The money has been deposited to the seller's account.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="warning">EXCEPTION</b-badge>
          <div>The order has exception, contact administrator for more information.</div>
        </b-list-group-item>
        <b-list-group-item>
          <b-badge variant="secondary">CANCELED</b-badge>
          <div>The order is canceled.</div>
        </b-list-group-item>

      </b-list-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderStatusHint"
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
