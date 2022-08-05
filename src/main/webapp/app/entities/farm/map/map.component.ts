import { AfterViewInit, Component } from '@angular/core';

import * as L from 'leaflet';
import 'leaflet-draw';


@Component({
  selector: 'jhi-root',
  templateUrl: './map.html',
  styleUrls: ['./map.scss'],
})
export class MapComponent implements AfterViewInit {

  map: any;
  
 

  public ngAfterViewInit(): void {
    this.loadMap();
  }

  
  private loadMap(): void {
    this.map = L.map('map', {
      center: [ 39.8282, -98.5795 ],
      zoom: 3
    });

    const tiles = L.tileLayer('http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
      maxZoom: 20,
    subdomains:['mt0','mt1','mt2','mt3']
    });


    tiles.addTo(this.map);


   const drawnItems = new L.FeatureGroup();
    
    this.map.addLayer(drawnItems);
     const drawControl = new L.Control.Draw({
      position: 'topright',
      draw: {
        polygon: false,
        polyline: false,
    
        circle: false,
        rectangle:false,
    },
     });
     

    
this.map.addControl(drawControl);

const editableLayers = new L.FeatureGroup();
this.map.addLayer(editableLayers);

this.map.on('draw:created', function(e:any) {
  const type = e.layerType,
    layer = e.layer;
    if (type === 'marker') {
      layer.on("click",(event:any)=>{
location.href="/farm/new"
      });
  }



  editableLayers.addLayer(layer);
});
   


}
}