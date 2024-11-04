import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import SeatService from './seat.service';
import { type ISeat } from '@/shared/model/seat.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SeatDetails',
  setup() {
    const seatService = inject('seatService', () => new SeatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const seat: Ref<ISeat> = ref({});

    const retrieveSeat = async seatId => {
      try {
        const res = await seatService().find(seatId);
        seat.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.seatId) {
      retrieveSeat(route.params.seatId);
    }

    return {
      alertService,
      seat,

      previousState,
      t$: useI18n().t,
    };
  },
});
