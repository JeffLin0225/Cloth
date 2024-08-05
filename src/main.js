import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import FontAwesomeIcon from "./plugins/fontawesome.js";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'

createApp(App)
    .use(router)
    .component("font-awesome-icon", FontAwesomeIcon)
    .mount('#app')
