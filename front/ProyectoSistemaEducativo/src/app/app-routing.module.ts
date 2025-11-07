import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { AdminPanelComponent } from "./components/admin-panel/admin-panel.component";
import { AlumnoPanelComponent } from "./components/alumno-panel/alumno-panel.component";
import { DocentePanelComponent } from "./components/docente-panel/docente-panel.component";

const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'admin', component: AdminPanelComponent },
    { path: 'alumno', component: AlumnoPanelComponent },
    { path: 'docente', component: DocentePanelComponent },
    { path: '**', redirectTo: 'login' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {}