<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
        <div> users: {{ userCount }} </div>
        <div> posts: {{ postCount }} </div>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["userCount", "postCount"]
}
</script>

<style scoped>

</style>