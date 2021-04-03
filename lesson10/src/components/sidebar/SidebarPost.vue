<template>
  <section>
    <div class="header" @click.prevent="showPost()">
      {{ sidebarPost.title }}
    </div>
    <div class="body" >
      {{ sidebarPost.text }}
    </div>
    <div class="footer">
      <a href="#">View all</a>
    </div>
  </section>
</template>

<script>
export default {
  name: "SidebarPost",
  props: ["sidebarPost"],
  methods: {
    showPost: function () {
      this.$root.$emit("showPost", this.sidebarPost);
    }
  }
}
</script>

<style scoped>
section {
  margin-bottom: 1rem;
  overflow: hidden;
}
</style>