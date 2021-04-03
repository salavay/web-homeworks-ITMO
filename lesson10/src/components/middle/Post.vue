<template>
  <div class="tablePosts">
    <MiddlePost :post="post"
                :comments="viewComments(post)"
                :users="users"
                :renderComments="true"/>
  </div>
</template>

<script>
import MiddlePost from "@/components/middle/MiddlePost";

export default {
  name: "Index",
  components: {
    MiddlePost
  },
  props: ["post", "comments", "users"],
  methods: {
    viewComments: function (post) {
      return Object.values(this.comments).filter(x => x.postId === post.id).sort((a, b) => b.id - a.id);
    },
  }
}
</script>

<style scoped>

</style>