import React from 'react';
import './assets/scss/App.scss';
import Main from './component/main';
import Gallery from './component/gallery';
import Guestbook from './component/guestbook';
export default function App() {
    return (
        <Router>
            <Route path={'/'} element={<Main />}></Route>
            <Route path={'gallery'} element={<Gallery />}></Route>
            <Route path={'guestbook'} element={<Guestbook />}></Route>
            <Route path={'login'} element={<Login />}></Route>
            <Route path={'signup'} element={<Signup />}></Route>
            <Route path={'settings'} element={<Settings />}></Route>
            <Route path={'*'} element={<Error404 />}></Route>
        </Router>

    );
}