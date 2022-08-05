<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>
      <h1 class="item-title">User Membership</h1>
      <div>
        <b-table striped hover :items="items"></b-table>
      </div>
<!--      <b-list-group class="badge-font">-->
<!--        <b-list-group-item>-->
<!--          <b-badge variant="success">REGULAR</b-badge>-->
<!--          <div>Regular membership, user can buy or sell item </div>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <b-badge variant="warning">GOLD PRIME</b-badge>-->
<!--          <div>This item is sold, someone has already bought it. This item cannot be bought.</div>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <b-badge variant="info">DIAMOND PRIME</b-badge>-->
<!--          <div>This item is banned by BusyTiger administrator due violation of our community rule.</div>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <b-badge variant="secondary">BANNED</b-badge>-->
<!--          <div>This item is invisible to everyone except its seller.</div>-->
<!--        </b-list-group-item>-->
<!--        <b-list-group-item>-->
<!--          <b-badge variant="secondary">FROZEN</b-badge>-->
<!--          <div>This item is not available now and is being checked by BusyTiger administrator. For more information, contact 707973013@qq.com</div>-->
<!--        </b-list-group-item>-->

<!--      </b-list-group>-->
    </div>
  </div>

</template>

<script>
export default {
  name: "MemberShipHint",
  data() {
    return {
      items: [
        { MemberShip: 'REGULAR', Buy: 'Yes', Sell: 'Yes', ServiceFeeRate:'1%', FastShipping:'No',Price:'0￥/month' },
        { MemberShip: 'GOLDEN_PRIME', Buy: 'Yes', Sell: 'Yes', ServiceFeeRate:'0.5%', FastShipping:'Yes',Price:'10￥/month' },
        { MemberShip: 'DIAMOND_PRIME', Buy: 'Yes', Sell: 'Yes', ServiceFeeRate:'0%', FastShipping:'Yes',Price:'20￥/month' },
        { MemberShip: 'BANNED', Buy: 'No', Sell: 'No', ServiceFeeRate:'1%', FastShipping:'No',Price:'N/A' },
        { MemberShip: 'FROZEN', Buy: 'No', Sell: 'No', ServiceFeeRate:'1%', FastShipping:'No',Price:'N/A' },

      ]
    }
  }
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
