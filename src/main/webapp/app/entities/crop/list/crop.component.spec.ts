import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CropService } from '../service/crop.service';

import { CropComponent } from './crop.component';

describe('Crop Management Component', () => {
  let comp: CropComponent;
  let fixture: ComponentFixture<CropComponent>;
  let service: CropService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CropComponent],
    })
      .overrideTemplate(CropComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CropComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CropService);

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
    expect(comp.crops?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
