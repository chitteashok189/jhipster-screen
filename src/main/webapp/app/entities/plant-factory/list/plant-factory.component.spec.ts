import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PlantFactoryService } from '../service/plant-factory.service';

import { PlantFactoryComponent } from './plant-factory.component';

describe('PlantFactory Management Component', () => {
  let comp: PlantFactoryComponent;
  let fixture: ComponentFixture<PlantFactoryComponent>;
  let service: PlantFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PlantFactoryComponent],
    })
      .overrideTemplate(PlantFactoryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantFactoryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlantFactoryService);

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
    expect(comp.plantFactories?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
