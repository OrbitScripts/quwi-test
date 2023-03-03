package com.orbitsoft.quwi.ui.listFragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orbitsoft.quwi.MainActivity;
import com.orbitsoft.quwi.R;
import com.orbitsoft.quwi.databinding.FragmentListBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class ListFragment extends Fragment {

    private ListViewModel mViewModel;
    private FragmentListBinding binding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.topAppBar.setOnMenuItemClickListener(
                menuItem -> {
                    if (menuItem.getItemId() == R.id.logout) {
                        mViewModel.logout();
                        return true;
                    } else {
                        return false;
                    }
                }
        );
        observeChannelsResponse();
        observeLogoutResponse();
    }

    public void observeChannelsResponse() {
        compositeDisposable.add(
                mViewModel.getListChannelInfo().subscribe(channels -> {
                    binding.rvChannels.setAdapter(new ChannelsAdapter(getContext(), channels));
                })
        );
    }

    public void observeLogoutResponse() {
        compositeDisposable.add(
                mViewModel.getIsNeedLogout().subscribe(isNeed -> {
                    if (isNeed) {
                        ((MainActivity) getActivity()).goToAuth();
                    }
                })
        );
    }
}