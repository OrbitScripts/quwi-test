package com.orbitsoft.quwi.ui.authFragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orbitsoft.quwi.R;
import com.orbitsoft.quwi.databinding.FragmentAuthBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class AuthFragment extends Fragment {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    private AuthViewModel mViewModel;
    private FragmentAuthBinding binding;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        observeLoginResponse();
        observeLoginExceptionResponse();
        initUI();
    }

    public void initUI() {
        binding.btnLogin.setOnClickListener(btnView -> {
            binding.textFieldPassword.setErrorEnabled(false);
            String email = binding.textFieldEmail.getEditText().getText().toString();
            String password = binding.textFieldPassword.getEditText().getText().toString();
            if (email.length() > 5 && validateEmail(email) && password.length() > 1) {
                mViewModel.login(email, password);
            }
        });
    }

    public void observeLoginResponse() {
        compositeDisposable.add(
                mViewModel.getLoginResponse().subscribe(loginResponse -> {
                    if (!loginResponse.getToken().isEmpty() && loginResponse.getToken() != null) {
                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_authFragment_to_listFragment);
                    }
                })
        );
    }

    public void observeLoginExceptionResponse() {
        compositeDisposable.add(
                mViewModel.getIsNeedShowError().subscribe(isNeed -> {
                    if (isNeed) {
                        binding.textFieldPassword
                                .setError("Error happens. Please check email and password, " +
                                        "then try again");
                    }
                })
        );
    }


    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}