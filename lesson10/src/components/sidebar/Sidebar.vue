<template>
  <aside>
    <SidebarPost v-for="sidebarPost in posts" :sidebarPost="sidebarPost" :key="sidebarPost.id"/>
  </aside>
</template>

<script>
import SidebarPost from "@/components/sidebar/SidebarPost";

export default {
  name: "Sidebar",
  components: {
    SidebarPost
  },
  props: ["posts"]
}
</script>

<style scoped>

</style>