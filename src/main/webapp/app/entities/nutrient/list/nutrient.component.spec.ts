import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NutrientService } from '../service/nutrient.service';

import { NutrientComponent } from './nutrient.component';

describe('Nutrient Management Component', () => {
  let comp: NutrientComponent;
  let fixture: ComponentFixture<NutrientComponent>;
  let service: NutrientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NutrientComponent],
    })
      .overrideTemplate(NutrientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NutrientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NutrientService);

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
    expect(comp.nutrients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
