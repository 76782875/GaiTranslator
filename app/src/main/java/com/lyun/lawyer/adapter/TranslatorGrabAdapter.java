package com.lyun.lawyer.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.lawyer.databinding.ItemTranslatorGrabLayoutBinding;
import com.lyun.lawyer.viewmodel.TranslatorGrabItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorGrabAdapter extends BaseRecyclerAdapter<ItemTranslatorGrabLayoutBinding, TranslatorGrabItemViewModel> {
    public TranslatorGrabAdapter(List<TranslatorGrabItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }

    @Override
    public void viewBind(TranslatorGrabItemViewModel viewModel, ItemTranslatorGrabLayoutBinding viewDataBinding, int position) {
        viewModel.init(position);
        viewDataBinding.setMvvm(viewModel);
    }
}
