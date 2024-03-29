import { createBrowserRouter, IndexRouteObject, NonIndexRouteObject, useMatches } from 'react-router-dom';
import LoginView from "Frontend/views/LoginView";
import RegisterView from "Frontend/views/RegisterView";
import EmptyView from "Frontend/views/empty/EmptyView";
import ProfileView from "Frontend/views/ProfileView";
import GroupView from "Frontend/views/GroupView";
import MeetingView from "Frontend/views/MeetingView";
import PoiView from "Frontend/views/PoiView";
import NearbyPoiView from "Frontend/views/NearbyPoiView";

export type MenuProps = Readonly<{
  icon?: string;
  title?: string;
}>;

export type ViewMeta = Readonly<{ handle?: MenuProps }>;

type Override<T, E> = Omit<T, keyof E> & E;

export type IndexViewRouteObject = Override<IndexRouteObject, ViewMeta>;
export type NonIndexViewRouteObject = Override<
  Override<NonIndexRouteObject, ViewMeta>,
  {
    children?: ViewRouteObject[];
  }
>;
export type ViewRouteObject = IndexViewRouteObject | NonIndexViewRouteObject;

type RouteMatch = ReturnType<typeof useMatches> extends (infer T)[] ? T : never;

export type ViewRouteMatch = Readonly<Override<RouteMatch, ViewMeta>>;

export const useViewMatches = useMatches as () => readonly ViewRouteMatch[];

export const routes: readonly ViewRouteObject[] = [
  { path: '/', element: <EmptyView /> },
  { path: '/login', element: <LoginView /> },
  { path: '/register', element: <RegisterView />},
  {path: '/group', element: <GroupView/>},
  {path: '/meeting', element: <MeetingView/>},
  {path: '/point', element: <PoiView/>},
  {path: '/nearby', element: <NearbyPoiView />}
];

const router = createBrowserRouter([...routes]);
export default router;
