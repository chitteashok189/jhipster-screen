import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PlantService } from '../service/plant.service';

import { PlantComponent } from './plant.component';

describe('Plant Management Component', () => {
  let comp: PlantComponent;
  let fixture: ComponentFixture<PlantComponent>;
  let service: PlantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PlantComponent],
    })
      .overrideTemplate(PlantComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlantService);

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
    expect(comp.plants?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
