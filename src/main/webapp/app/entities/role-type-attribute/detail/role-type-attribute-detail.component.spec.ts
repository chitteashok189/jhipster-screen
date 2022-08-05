import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RoleTypeAttributeDetailComponent } from './role-type-attribute-detail.component';

describe('RoleTypeAttribute Management Detail Component', () => {
  let comp: RoleTypeAttributeDetailComponent;
  let fixture: ComponentFixture<RoleTypeAttributeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoleTypeAttributeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ roleTypeAttribute: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RoleTypeAttributeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RoleTypeAttributeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load roleTypeAttribute on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.roleTypeAttribute).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
