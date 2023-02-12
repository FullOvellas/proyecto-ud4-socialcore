import router from 'Frontend/routes.js';
import { RouterProvider } from 'react-router-dom';
import * as dotenv from 'dotenv';

export default function App() {
  dotenv.config()
  return <RouterProvider router={router} />;
}
