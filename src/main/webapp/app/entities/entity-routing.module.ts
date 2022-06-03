import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'way-to-pay',
        data: { pageTitle: 'tucampov3App.wayToPay.home.title' },
        loadChildren: () => import('./way-to-pay/way-to-pay.module').then(m => m.WayToPayModule),
      },
      {
        path: 'invoice',
        data: { pageTitle: 'tucampov3App.invoice.home.title' },
        loadChildren: () => import('./invoice/invoice.module').then(m => m.InvoiceModule),
      },
      {
        path: 'shopping',
        data: { pageTitle: 'tucampov3App.shopping.home.title' },
        loadChildren: () => import('./shopping/shopping.module').then(m => m.ShoppingModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'tucampov3App.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'vehicle',
        data: { pageTitle: 'tucampov3App.vehicle.home.title' },
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.VehicleModule),
      },
      {
        path: 'driver',
        data: { pageTitle: 'tucampov3App.driver.home.title' },
        loadChildren: () => import('./driver/driver.module').then(m => m.DriverModule),
      },
      {
        path: 'dats',
        data: { pageTitle: 'tucampov3App.dats.home.title' },
        loadChildren: () => import('./dats/dats.module').then(m => m.DatsModule),
      },
      {
        path: 'units',
        data: { pageTitle: 'tucampov3App.units.home.title' },
        loadChildren: () => import('./units/units.module').then(m => m.UnitsModule),
      },
      {
        path: 'products',
        data: { pageTitle: 'tucampov3App.products.home.title' },
        loadChildren: () => import('./products/products.module').then(m => m.ProductsModule),
      },
      {
        path: 'sale',
        data: { pageTitle: 'tucampov3App.sale.home.title' },
        loadChildren: () => import('./sale/sale.module').then(m => m.SaleModule),
      },
      {
        path: 'anonymous',
        data: { pageTitle: 'tucampov3App.anonymous.home.title' },
        loadChildren: () => import('./anonymous/anonymous.module').then(m => m.AnonymousModule),
      },
      {
        path: 'producer',
        data: { pageTitle: 'tucampov3App.producer.home.title' },
        loadChildren: () => import('./producer/producer.module').then(m => m.ProducerModule),
      },
      {
        path: 'administrator',
        data: { pageTitle: 'tucampov3App.administrator.home.title' },
        loadChildren: () => import('./administrator/administrator.module').then(m => m.AdministratorModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'tucampov3App.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'documen-type',
        data: { pageTitle: 'tucampov3App.documenType.home.title' },
        loadChildren: () => import('./documen-type/documen-type.module').then(m => m.DocumenTypeModule),
      },
      {
        path: 'order-detai',
        data: { pageTitle: 'tucampov3App.orderDetai.home.title' },
        loadChildren: () => import('./order-detai/order-detai.module').then(m => m.OrderDetaiModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
