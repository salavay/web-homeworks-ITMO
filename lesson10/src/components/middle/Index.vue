<template>
  <div class="tablePosts">
    <MiddlePost v-for="post in sortedPosts" :key="post.id"
                :post="post"
                :comments="viewComments(post)"
                :users="users"
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
  props: ["posts", "comments", "users"],
  computed: {
    sortedPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id);
    }
  },
  methods: {
    viewComments: function (post) {
      return Object.values(this.comments).filter(x => x.postId === post.id).sort((a, b) => b.id - a.id);
    },
  }
}
</script>

<style scoped>

</style>