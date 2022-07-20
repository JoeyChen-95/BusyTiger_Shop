export function toastMessage(content){
  this.$bvToast.toast(content, {
    title: 'Tips',
    autoHideDelay: 2000,
    variant: 'warning',
    solid: true,
    appendToast: false
  });
}
