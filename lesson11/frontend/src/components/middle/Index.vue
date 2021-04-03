<template>
  <div class="tablePosts">
    <MiddlePost v-for="post in posts" :key="post.id"
                :post="post"
                :renderComments="false"/>
  </div>
</template>

<script>
import MiddlePost from "@/components/middle/MiddlePost";

export default {
  name: "Index",
  components: {
    MiddlePost
  },
  props: ["posts"]
}
</script>

<style scoped>

</style>