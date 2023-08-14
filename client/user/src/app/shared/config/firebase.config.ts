import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import firebase from 'firebase/compat/app';
import 'firebase/compat/storage';

const firebaseConfig = {
    apiKey: "AIzaSyDU-I7dtl0OePvQ9Vm9CORNpZKCbjQZmSc",
    authDomain: "rentx-bb406.firebaseapp.com",
    projectId: "rentx-bb406",
    storageBucket: "rentx-bb406.appspot.com",
    messagingSenderId: "496739751259",
    appId: "1:496739751259:web:2a3ad371cd9767f5acf67e",
    measurementId: "G-1CPQLM7V4E"
  };

firebase.initializeApp(firebaseConfig);

export const firebaseStorage = firebase.storage();
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
