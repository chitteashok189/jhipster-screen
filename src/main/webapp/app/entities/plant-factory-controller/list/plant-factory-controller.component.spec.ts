import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';

import { PlantFactoryControllerComponent } from './plant-factory-controller.component';

describe('PlantFactoryController Management Component', () => {
  let comp: PlantFactoryControllerComponent;
  let fixture: ComponentFixture<PlantFactoryControllerComponent>;
  let service: PlantFactoryControllerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PlantFactoryControllerComponent],
    })
      .overrideTemplate(PlantFactoryControllerComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantFactoryControllerComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlantFactoryControllerService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.plantFactoryControllers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
